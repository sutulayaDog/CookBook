using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace CookBookAPI.Models
{
    public partial class Resipe : ModelShare
    {
        public Resipe()
        {
            ProductInResipe = new HashSet<ProductInResipe>();
        }

        [JsonProperty("idResipe")]
        public int IdResipe { get; set; }
        [JsonProperty("idCategorie")]
        public int? IdCategorie { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; }
        [JsonProperty("description")]
        public string Description { get; set; }
        [JsonProperty("timeCook")]
        public string TimeCook { get; set; }
        [JsonProperty("dateLastChange")]
        public int DateLastChange { get; set; }
        [JsonProperty("timeLastChange")]
        public int TimeLastChange { get; set; }
        [JsonProperty("status")]
        public string Status { get; set; }

        [JsonIgnore]
        public virtual Categorie IdCategorieNavigation { get; set; }
        [JsonIgnore]
        public virtual ICollection<ProductInResipe> ProductInResipe { get; set; }
    }
}
