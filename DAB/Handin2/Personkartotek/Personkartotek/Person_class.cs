using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Personkartotek
{
    public class Person_class
    {
        [Key]
        public String fullName { get; set; }

        public String stateRegisterAddress { get; set; }

        public int type { get; set; }
    }
}
