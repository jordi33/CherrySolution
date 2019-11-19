using AspectInjector.Broker;
using System;
using System.Diagnostics;


namespace Monitoring
{
    [Aspect(Scope.Global)]
    [Injection(typeof(ExecutionTime))]
    public class ExecutionTime : Attribute
    {
        [Advice(Kind.Around, Targets = Target.Method)]
        public object HandleMethod(
            [Argument(Source.Name)] string name,
            [Argument(Source.Arguments)] object[] arguments,
            [Argument(Source.Target)] Func<object[], object> method)
        {

            var sw = Stopwatch.StartNew();

            var result = method(arguments);

            sw.Stop();

            Console.WriteLine($"{name} in {sw.ElapsedMilliseconds} ms");

            return result;
        }
    }
}
