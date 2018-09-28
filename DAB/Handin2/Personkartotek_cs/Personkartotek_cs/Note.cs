using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class Note
    {

        [Key]
        public int noteId { get; set; }

        public string note { get; set; }       
    }
}