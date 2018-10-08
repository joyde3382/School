using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs
{
    public class Note
    {

        [Key, Index(IsUnique = true)]
        public int noteId { get; set; }


        [Required]
        public int personId { get; set; }

        public string note { get; set; }       
    }
}