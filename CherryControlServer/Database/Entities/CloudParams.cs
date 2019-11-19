using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistance.Entities
{
    public class CloudParams
    {
        public ObjectId Id { get; set; }
        public string SttLangCode { get; set; }
        public int? SttSampleRate { get; set; }
        public bool? SttInterimResults { get; set; }
        public bool? SttSingleUtterance { get; set; }
        public int? SttResetTime { get; set; }
        public string ApiKeyPath { get; set; }
        public string TtsLangCode { get; set; }
        public string TtsName { get; set; }
        public double? TtsPich { get; set; }
        public double? TtsRate { get; set; }
        public double? TtsGain { get; set; }
        public string TmpDir { get; set; }
        public string DfToken { get; set; }
    }
}
