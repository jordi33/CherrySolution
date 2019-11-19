namespace CloudServices
{
    public delegate void DfResultHandler(string text);
    public interface IDialogService
    {
        event DfResultHandler OnResult;
        void Answer(string text);
    }
}
