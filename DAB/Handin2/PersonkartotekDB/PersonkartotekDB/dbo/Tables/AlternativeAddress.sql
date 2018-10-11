CREATE TABLE [dbo].[AlternativeAddress] (
    [AAId]                      INT            IDENTITY (1, 1) NOT NULL,
    [addressType]               NVARCHAR (MAX) NULL,
    [attachedAddress_addressId] INT            NULL,
    CONSTRAINT [PK_dbo.AlternativeAddress] PRIMARY KEY CLUSTERED ([AAId] ASC),
    CONSTRAINT [FK_dbo.AlternativeAddress_dbo.Address_attachedAddress_addressId] FOREIGN KEY ([attachedAddress_addressId]) REFERENCES [dbo].[Address] ([addressId])
);


GO
CREATE NONCLUSTERED INDEX [IX_attachedAddress_addressId]
    ON [dbo].[AlternativeAddress]([attachedAddress_addressId] ASC);

