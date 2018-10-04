namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Note : DbMigration
    {
        public override void Up()
        {
            RenameColumn(table: "dbo.Person_Class", name: "note_noteId", newName: "personNote_noteId");
            RenameIndex(table: "dbo.Person_Class", name: "IX_note_noteId", newName: "IX_personNote_noteId");
        }
        
        public override void Down()
        {
            RenameIndex(table: "dbo.Person_Class", name: "IX_personNote_noteId", newName: "IX_note_noteId");
            RenameColumn(table: "dbo.Person_Class", name: "personNote_noteId", newName: "note_noteId");
        }
    }
}
