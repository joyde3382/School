namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class uniqueAddress1 : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Addresses", "cityAtAddress_cityId", "dbo.Cities");
            DropIndex("dbo.Addresses", new[] { "streetAddress" });
            DropIndex("dbo.Addresses", new[] { "cityAtAddress_cityId" });
            AlterColumn("dbo.Addresses", "streetAddress", c => c.String());
            AlterColumn("dbo.Addresses", "cityAtAddress_cityId", c => c.Int());
            CreateIndex("dbo.Addresses", "cityAtAddress_cityId");
            AddForeignKey("dbo.Addresses", "cityAtAddress_cityId", "dbo.Cities", "cityId");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Addresses", "cityAtAddress_cityId", "dbo.Cities");
            DropIndex("dbo.Addresses", new[] { "cityAtAddress_cityId" });
            AlterColumn("dbo.Addresses", "cityAtAddress_cityId", c => c.Int(nullable: false));
            AlterColumn("dbo.Addresses", "streetAddress", c => c.String(maxLength: 128));
            CreateIndex("dbo.Addresses", "cityAtAddress_cityId");
            CreateIndex("dbo.Addresses", "streetAddress", unique: true);
            AddForeignKey("dbo.Addresses", "cityAtAddress_cityId", "dbo.Cities", "cityId", cascadeDelete: true);
        }
    }
}
