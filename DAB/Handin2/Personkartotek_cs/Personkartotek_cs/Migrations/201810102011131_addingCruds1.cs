namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class addingCruds1 : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Person", "addressId", "dbo.Address");
            DropIndex("dbo.Person", new[] { "addressId" });
            AlterColumn("dbo.Person", "addressId", c => c.Int());
            CreateIndex("dbo.Person", "addressId");
            AddForeignKey("dbo.Person", "addressId", "dbo.Address", "addressId");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Person", "addressId", "dbo.Address");
            DropIndex("dbo.Person", new[] { "addressId" });
            AlterColumn("dbo.Person", "addressId", c => c.Int(nullable: false));
            CreateIndex("dbo.Person", "addressId");
            AddForeignKey("dbo.Person", "addressId", "dbo.Address", "addressId", cascadeDelete: true);
        }
    }
}
