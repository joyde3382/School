namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Person2 : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.AlternativeAddresses", "attachedPerson_StateRegisterAddress", "dbo.Person_Class");
            DropForeignKey("dbo.AlternativeContactInfoes", "Person_Class_StateRegisterAddress", "dbo.Person_Class");
            RenameColumn(table: "dbo.AlternativeAddresses", name: "attachedPerson_StateRegisterAddress", newName: "attachedPerson_fullName");
            RenameColumn(table: "dbo.AlternativeContactInfoes", name: "Person_Class_StateRegisterAddress", newName: "Person_Class_fullName");
            RenameIndex(table: "dbo.AlternativeAddresses", name: "IX_attachedPerson_StateRegisterAddress", newName: "IX_attachedPerson_fullName");
            RenameIndex(table: "dbo.AlternativeContactInfoes", name: "IX_Person_Class_StateRegisterAddress", newName: "IX_Person_Class_fullName");
            DropPrimaryKey("dbo.Person_Class");
            AlterColumn("dbo.Person_Class", "StateRegisterAddress", c => c.String());
            AlterColumn("dbo.Person_Class", "display_name", c => c.String(nullable: false, maxLength: 128));
            AddPrimaryKey("dbo.Person_Class", "display_name");
            AddForeignKey("dbo.AlternativeAddresses", "attachedPerson_fullName", "dbo.Person_Class", "display_name");
            AddForeignKey("dbo.AlternativeContactInfoes", "Person_Class_fullName", "dbo.Person_Class", "display_name");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.AlternativeContactInfoes", "Person_Class_fullName", "dbo.Person_Class");
            DropForeignKey("dbo.AlternativeAddresses", "attachedPerson_fullName", "dbo.Person_Class");
            DropPrimaryKey("dbo.Person_Class");
            AlterColumn("dbo.Person_Class", "display_name", c => c.String());
            AlterColumn("dbo.Person_Class", "StateRegisterAddress", c => c.String(nullable: false, maxLength: 128));
            AddPrimaryKey("dbo.Person_Class", "StateRegisterAddress");
            RenameIndex(table: "dbo.AlternativeContactInfoes", name: "IX_Person_Class_fullName", newName: "IX_Person_Class_StateRegisterAddress");
            RenameIndex(table: "dbo.AlternativeAddresses", name: "IX_attachedPerson_fullName", newName: "IX_attachedPerson_StateRegisterAddress");
            RenameColumn(table: "dbo.AlternativeContactInfoes", name: "Person_Class_fullName", newName: "Person_Class_StateRegisterAddress");
            RenameColumn(table: "dbo.AlternativeAddresses", name: "attachedPerson_fullName", newName: "attachedPerson_StateRegisterAddress");
            AddForeignKey("dbo.AlternativeContactInfoes", "Person_Class_StateRegisterAddress", "dbo.Person_Class", "StateRegisterAddress");
            AddForeignKey("dbo.AlternativeAddresses", "attachedPerson_StateRegisterAddress", "dbo.Person_Class", "StateRegisterAddress");
        }
    }
}
