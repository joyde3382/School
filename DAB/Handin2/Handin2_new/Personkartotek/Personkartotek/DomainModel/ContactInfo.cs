using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Personkartotek.DomainModel
{
    public class ContactInfo
    {
        public virtual int contactId { get; set; }

        public virtual int phoneNumber { get; set; }

        public virtual string phoneType { get; set; }

        public virtual string phoneCompany { get; set; }
    }
}