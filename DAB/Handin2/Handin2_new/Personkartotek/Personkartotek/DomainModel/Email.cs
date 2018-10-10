using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Personkartotek.DomainModel
{
    public class Email
    {
        public virtual long emailId { get; set; }

        public virtual string email { get; set; }
    }
}