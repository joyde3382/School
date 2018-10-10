using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Personkartotek.DomainModel
{
    public class AA
    {
        public virtual string addressType { get; set; }

        public virtual long addressId { get; set; }

        public virtual List<Person> attachedPerson { get; set; }

        public virtual Address attachedAddress { get; set; }

    }
}
