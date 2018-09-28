using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Personkartotek
{
    public class AlternativeContactInfo
    {
        public string company { get; set; }

        [Key]
        public int phoneNumber { get; set; }
    }
}
