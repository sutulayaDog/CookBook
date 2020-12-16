using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace CookBookAPI.Models
{
    public partial class CookbookDbContext : DbContext
    {
        public CookbookDbContext()
        {
        }

        public CookbookDbContext(DbContextOptions<CookbookDbContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Categorie> Categorie { get; set; }
        public virtual DbSet<Product> Product { get; set; }
        public virtual DbSet<ProductInResipe> ProductInResipe { get; set; }
        public virtual DbSet<Resipe> Resipe { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Categorie>(entity =>
            {
                entity.HasKey(e => e.IdCategorie)
                    .HasName("PRIMARY");

                entity.Property(e => e.IdCategorie).HasColumnType("int(11)");

                entity.Property(e => e.DateLastChange).HasColumnType("int(11)");

                entity.Property(e => e.Name)
                    .HasColumnType("varchar(50)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.Status)
                    .HasColumnType("char(1)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.TimeLastChange).HasColumnType("int(11)");
            });

            modelBuilder.Entity<Product>(entity =>
            {
                entity.HasKey(e => e.IdProduct)
                    .HasName("PRIMARY");

                entity.Property(e => e.IdProduct).HasColumnType("int(11)");

                entity.Property(e => e.DateLastChange).HasColumnType("int(11)");

                entity.Property(e => e.Name)
                    .HasColumnType("varchar(50)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.Status)
                    .HasColumnType("char(1)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.TimeLastChange).HasColumnType("int(11)");
            });

            modelBuilder.Entity<ProductInResipe>(entity =>
            {
                entity.HasKey(e => e.IdProductInResipe)
                    .HasName("PRIMARY");

                entity.ToTable("Product_in_Resipe");

                entity.HasIndex(e => e.IdProduct)
                    .HasName("IdProduct");

                entity.HasIndex(e => e.IdResipe)
                    .HasName("IdResipe");

                entity.Property(e => e.IdProductInResipe)
                    .HasColumnName("IdProductInResipe")
                    .HasColumnType("int(11)");

                entity.Property(e => e.DateLastChange).HasColumnType("int(11)");

                entity.Property(e => e.IdProduct).HasColumnType("int(11)");

                entity.Property(e => e.IdResipe).HasColumnType("int(11)");

                entity.Property(e => e.Quantity)
                    .HasColumnType("varchar(50)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.Status)
                    .HasColumnType("char(1)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.TimeLastChange).HasColumnType("int(11)");

                entity.HasOne(d => d.IdProductNavigation)
                    .WithMany(p => p.ProductInResipe)
                    .HasForeignKey(d => d.IdProduct)
                    .HasConstraintName("Product_in_Resipe_ibfk_1");

                entity.HasOne(d => d.IdResipeNavigation)
                    .WithMany(p => p.ProductInResipe)
                    .HasForeignKey(d => d.IdResipe)
                    .HasConstraintName("Product_in_Resipe_ibfk_2");
            });

            modelBuilder.Entity<Resipe>(entity =>
            {
                entity.HasKey(e => e.IdResipe)
                    .HasName("PRIMARY");

                entity.HasIndex(e => e.IdCategorie)
                    .HasName("IdCategorie");

                entity.Property(e => e.IdResipe).HasColumnType("int(11)");

                entity.Property(e => e.DateLastChange).HasColumnType("int(11)");

                entity.Property(e => e.Description)
                    .HasColumnType("text")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.IdCategorie).HasColumnType("int(11)");

                entity.Property(e => e.Name)
                    .HasColumnType("varchar(50)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.Status)
                    .HasColumnType("char(1)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.TimeCook)
                    .HasColumnType("varchar(50)")
                    .HasCharSet("utf8")
                    .HasCollation("utf8_general_ci");

                entity.Property(e => e.TimeLastChange).HasColumnType("int(11)");

                entity.HasOne(d => d.IdCategorieNavigation)
                    .WithMany(p => p.Resipe)
                    .HasForeignKey(d => d.IdCategorie)
                    .HasConstraintName("Resipe_ibfk_1");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
