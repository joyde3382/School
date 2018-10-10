using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Personkartotek.DomainModel
{
    public class Person
    {
        public Person()
        {
            Address = new Address();

            EmailAddress = new List<Email>();

            ContactInfo = new List<ContactInfo>();

            personNote = new List<Note>();

            AlternativeAddress = new List<AA>();
        }

        public virtual long PID { get; set; }
        public virtual long addressId { get; set; }
        public virtual string fullName { get; set; }


        public virtual string personType { get; set; }

        public virtual List<AA> AlternativeAddress { get; set; }

        public virtual Address Address { get; set; }

        public virtual List<Email> EmailAddress { get; set; }

        public virtual List<ContactInfo> ContactInfo { get; set; }

        public virtual List<Note> personNote { get; set; }
    }
}