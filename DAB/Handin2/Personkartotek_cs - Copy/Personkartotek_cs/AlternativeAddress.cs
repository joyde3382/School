using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class AlternativeAddress
    {
        [Key]
        public string addressType { get; set; }

 
        
        public string streetAddress { get; set; }

        public string countryRegion { get; set; }


        public int zipCode { get; set; }


        public string cityName { get; set; }

        public Person_Class attachedPerson { get; set; }

        public PrimaryAddress attachedAddress { get; set; }


    }
}