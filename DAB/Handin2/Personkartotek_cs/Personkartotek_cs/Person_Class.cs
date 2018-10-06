using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class Person_Class
    {
        public Person_Class()
        {
            Address = new List<Address>();

            EmailAddress = new List<Email>();
            
            ContactInfo = new List<ContactInfo>();
        }

        [Key, Index(IsUnique = true)]
        public int personId { get; set; }
        public string fullName { get; set; }

        
        public PersonType personType { get; set; }

        public Note personNote { get; set; } // needs fix

        [Required]
        public virtual List<Address> Address { get; set; }

        public virtual List<Email> EmailAddress { get; set; }

        public virtual List<ContactInfo> ContactInfo { get; set; }
    }
}