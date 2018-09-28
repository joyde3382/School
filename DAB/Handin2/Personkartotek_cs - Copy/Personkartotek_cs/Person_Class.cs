using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class Person_Class
    {
        
        public string StateRegisterAddress { get; set; }
        [Key]
        public string fullName { get; set; }

        
        public string type { get; set; }

        public Note note { get; set; }

        public Email PrimaryEmailAddress { get; set; }

        public PrimaryAddress PrimaryAddress { get; set; }
        public List<AlternativeAddress> AlternativeAddresses { get; set; }

        public PrimaryContactInfo PrimaryContactInfo { get; set; }

        public List<AlternativeContactInfo> AlternativeContactInfo { get; set; }


    }
}