# It's our own version of PoppyTorso install script. The one that's provided by creators of Poppy is not working and overcomplicates things
#! /bin/bash

if [ `whoami` != "root" ];
then
    echo "You must run the app as root:"
    echo "sudo bash $0"
    exit 0
fi

# Wait internet

while true
do
    wget -q --tries=10 --timeout=20 -O - http://google.com > /dev/null
    if [[ $? -eq 0 ]]; then
        break
    fi
done

install_puppet_master() 
{
    pushd "/home/odroid/"
        wget https://github.com/poppy-project/puppet-master/archive/master.zip
        unzip master.zip
        rm master.zip
        mv puppet-master-master puppet-master

        pushd puppet-master
            pip install flask pyyaml requests

            python bootstrap.py poppy poppy-torso --board "Odroid"
            #install_snap "$(pwd)"
        popd
    popd
}

install_opencv(){
echo 'Installing OpenCV'
    # Get out if opencv is already installed
    if $(python -c "import cv2" &> /dev/null); then
        echo "opencv is already installed"
        return
    fi

    pushd $POPPY_ROOT
        sudo apt-get install -y build-essential cmake pkg-config libgtk2.0-dev libjpeg8-dev libtiff5-dev libjasper-dev libpng12-dev libavcodec-dev libavformat-dev libswscale-dev libv4l-dev libatlas-base-dev gfortran python-dev python-numpy
        wget https://github.com/Itseez/opencv/archive/3.1.0.tar.gz -O opencv.tar.gz
        tar xvfz opencv.tar.gz
        rm opencv.tar.gz
        pushd opencv-3.1.0
            mkdir -p build
            pushd build
                cmake -D BUILD_PERF_TESTS=OFF -D BUILD_TESTS=OFF -D PYTHON_EXECUTABLE=/usr/bin/python ..
                make -j4
                sudo make install
                ln -s /usr/local/lib/python2.7/dist-packages/cv2.so $HOME/.pyenv/versions/2.7.11/lib/python2.7/cv2.so
            popd
        popd
    popd
echo '\t __done'
}

redirect_port80_webinterface(){
echo 'Redirecting :80 port'
    cat > firewall << EOF
#!/bin/sh

PATH=/sbin:/bin:/usr/sbin:/usr/bin

# Flush any existing firewall rules we might have
iptables -F
iptables -t nat -F
iptables -t mangle -F
iptables -X

# Perform the rewriting magic.
iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to 2280
EOF
    chmod +x firewall
    sudo chown root:root firewall
    sudo mv firewall /etc/network/if-up.d/firewall
echo '\t __done'
}

###### Optional
install_snap(){
echo 'Installing Snap'
    pushd $1
        wget https://github.com/jmoenig/Snap--Build-Your-Own-Blocks/archive/master.zip -O master.zip
        unzip master.zip
        rm master.zip
        mv Snap--Build-Your-Own-Blocks-master snap

        pypot_root=$(python -c "import pypot, os; print(os.path.dirname(pypot.__file__))")
        ln -s $pypot_root/server/snap_projects/pypot-snap-blocks.xml snap/libraries/poppy.xml
        echo -e "poppy.xml\tPoppy robots" >> snap/libraries/LIBRARIES

        # Delete snap default examples
        rm snap/Examples/EXAMPLES
        
        # Link pypot Snap projets to Snap! Example folder
        for project in $pypot_root/server/snap_projects/*.xml do
            ln -s $project snap/Examples/

            filename=$(basename "$project")
            echo -e "$filename\t$filename" >> snap/Examples/EXAMPLES
        done

        wget https://github.com/poppy-project/poppy-monitor/archive/master.zip -O master.zip
        unzip master.zip
        rm master.zip
        mv poppy-monitor-master monitor
    popd
	echo '\t __done'
}

set_up_poppy_packages(){
echo 'Installing poppy specific packages'
pip install pypot 
pip install poppy-torso
echo '\t __done'
}

set_up_software(){
echo 'Installing standart packages'

sudo apt-get install -y curl git htop
sudo apt-get install -y make build-essential libssl-dev zlib1g-dev libbz2-dev libreadline-dev libsqlite3-dev wget llvm
sudo apt-get install -y libfreetype6-dev libpng++-dev
sudo apt-get -y install libc6 libgcc1 libgfortran3 libstdc++6 build-essential gfortran python-all-dev libatlas-base-dev #libblas3gf , liblapack3gf
echo 'Installing pip'
curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
python get-pip.py
echo 'Installing python packages'
pip install jupyter
pip install numpy
pip install scipy
pip install matplotlib
echo '\t __done'
}

set_no_password(){
echo 'Allow odroid to use sudo without password'
chmod +w /etc/sudoers
echo "odroid ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
chmod -w /etc/sudoers
usermod --pass='*' root # don't need root password any more
export HOME=/home/odroid
echo '\t __done'
}

install_poppy_environment(){
echo 'Updating OS'
sudo apt-get update --fix-missing
sudo apt-get upgrade
echo '\t __done'
echo 'Starting the Poppy environement installation'
set_no_password()
set_up_software()
set_up_poppy_packages()
install_puppet_master()
redirect_port80_webinterface()
install_opencv()
echo 'System install complete!'

echo "Your system will now reboot..."
sudo reboot
}

install_poppy_environment()