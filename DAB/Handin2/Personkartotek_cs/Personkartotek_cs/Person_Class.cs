using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class Person_Class
    {
        [Key]
        public int personId { get; set; }
        public string fullName { get; set; }

        
        public PersonType type { get; set; }

        public Note note { get; set; }

        public List<Address> Address { get; set; }

        public List<Email> EmailAddress { get; set; }


        public List<ContactInfo> ContactInfo { get; set; }
    }
}