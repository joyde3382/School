using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Personkartotek.DomainModel
{
    public class Address
    {
        public virtual long addressId { get; set; }

        public virtual string streetAddress { get; set; }

        public virtual List<Person> peopleAtAddress { get; set; }

        public virtual City cityAtAddress { get; set; }

    }
}