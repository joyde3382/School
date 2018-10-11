CREATE TABLE [dbo].[Email] (
    [emailId]         INT           IDENTITY (1, 1) NOT NULL,
    [email]           NVARCHAR (64) NULL,
    [person_personId] INT           NOT NULL,
    CONSTRAINT [PK_dbo.Email] PRIMARY KEY CLUSTERED ([emailId] ASC),
    CONSTRAINT [FK_dbo.Email_dbo.Person_person_personId] FOREIGN KEY ([person_personId]) REFERENCES [dbo].[Person] ([personId]) ON DELETE CASCADE
);


GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_emailId]
    ON [dbo].[Email]([emailId] ASC);


GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_email]
    ON [dbo].[Email]([email] ASC);


GO
CREATE NONCLUSTERED INDEX [IX_person_personId]
    ON [dbo].[Email]([person_personId] ASC);

