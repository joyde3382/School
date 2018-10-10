using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs.DomainModel
{
    [Table("AlternativeAddress")]
    public class AlternativeAddress
    {
        public virtual string addressType { get; set; }

        [Key, Index(IsUnique = true)]
        public virtual long AAId { get; set; }

        public virtual System.Collections.Generic.List<Person> attachedPerson { get; set; }

        public virtual Address attachedAddress { get; set; }
    }
}