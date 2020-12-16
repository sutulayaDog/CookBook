using Newtonsoft.Json;

namespace CookBookAPI.Models
{
    public abstract class ModelShare{
        public override string ToString()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }
    }
}