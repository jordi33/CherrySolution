using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using CloudServices.Impl;
using System.IO;
using Communications;
using System.Threading.Tasks;
using CherryController.Core;

namespace TestCherryControlerServer
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void GCP_DF_1()
        {
            DialogFlow dialogFlow = new DialogFlow();
            dialogFlow.OnResult += DialogFlow_OnResult;
            dialogFlow.Answer("GCP_DF_A_TEST_CASE");
        }
        
        [TestMethod]
        public void GCP_TTS_1()
        {

           
                GoogleTts googleTts = new GoogleTts();
                googleTts.OnResult += GoogleTts_OnResult;
                googleTts.Pronounce("Test de génération de la voix");


        }

        [TestMethod]
        public void GCP_STT_1()
        {
            byte[] bytes = File.ReadAllBytes(@"..\..\GCP_STT_1_test_file.wav");
            GoogleStt googleStt = new GoogleStt();
            googleStt.OnResult += GoogleStt_OnResult;
            googleStt.Transcribe(bytes);
            
            
        }

        [TestMethod]
        public async Task SOCKET_1()
        {
            try
            {
                WebSocketServer socketServer = new WebSocketServer();
                socketServer.StartServer();
                int first = socketServer._commandBus._controller._connectedDevices.Count;
                MocPoppy poppy = new MocPoppy();
                await poppy.connect();
                int after = socketServer._commandBus._controller._connectedDevices.Count;
                Assert.AreEqual(after, first + 1);
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private void GoogleStt_OnResult(string text)
        {
            Assert.AreEqual(text, "Test de reconnaissance de la voix");
        }

        private void GoogleTts_OnResult(string text, int duration)
        {
            string mp3File = "../../../tmp/" + text;
            Assert.IsTrue(File.Exists(mp3File));
            var tfile = TagLib.File.Create(mp3File);
            Assert.AreEqual(tfile.Properties.Duration, duration);
            byte[] origin = File.ReadAllBytes(@"..\..\test.mp3");
            byte[] generate = File.ReadAllBytes(mp3File);
            Assert.AreEqual(origin, generate);
            //Assert.IsNotNull(text);
            //Assert.IsTrue(duration > 0);
        }
        
        private void DialogFlow_OnResult(string text)
        {
            Assert.AreEqual(text, "GCP_DF_A_TEST_CASE_SUCCESS");
        }
    }
}
