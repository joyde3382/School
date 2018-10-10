using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs.DomainModel
{
    [Table("Person")]
    public class Person
    {
        public Person()
        {
            Address = new Address();

            EmailAddress = new List<Email>();

            ContactInfo = new List<ContactInfo>();

            personNote = new List<Note>();

            AlternativeAddress = new List<AlternativeAddress>();
        }

        [Key, Index(IsUnique = true)]
        public virtual int personId { get; set; }
        public virtual string fullName { get; set; }

        public virtual string personType { get; set; }

        
        public virtual int? addressId { get; set; }


        //[Required]
        public virtual Address Address { get; set; }

        public virtual List<AlternativeAddress> AlternativeAddress { get; set; }

        public virtual List<Email> EmailAddress { get; set; }

        public virtual List<ContactInfo> ContactInfo { get; set; }

        public virtual List<Note> personNote { get; set; }
    }
}