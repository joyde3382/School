using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class City
    {
        [Key, Index(IsUnique = true)]
        public int cityId { get; set; }

        public string cityName { get; set; }

        public int zipCode { get; set; }


    }
}