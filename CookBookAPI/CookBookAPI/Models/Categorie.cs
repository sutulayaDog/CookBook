using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace CookBookAPI.Models
{
    public partial class Categorie : ModelShare
    {
        public Categorie()
        {
            Resipe = new HashSet<Resipe>();
        }

        [JsonProperty("idCategorie")]
        public int IdCategorie { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; }
        [JsonProperty("dateLastChange")]
        public int DateLastChange { get; set; }
        [JsonProperty("timeLastChange")]
        public int TimeLastChange { get; set; }
        [JsonProperty("status")]
        public string Status { get; set; }

        public virtual ICollection<Resipe> Resipe { get; set; }
    }
}
