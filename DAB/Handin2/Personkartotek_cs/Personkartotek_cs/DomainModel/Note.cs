using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;

namespace Personkartotek_cs.DomainModel
{
    [Table("Note")]
    public class Note
    {

        [Key, Index(IsUnique = true)]
        public virtual int noteId { get; set; }


        [Required]
        public virtual Person personId { get; set; }

        public virtual string note { get; set; }       
    }
}