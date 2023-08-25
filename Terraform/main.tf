provider "aws" {
  region     = "us-west-2" # this tells terraform where we want our resources created
  profile    = "terraform-user"
}

# module "ec2-instance" { # you can see more of this module's info in the .terraform/modules directory
#   source  = "terraform-aws-modules/ec2-instance/aws"
#   version = "~> 3.0"

#   name = "terraform-p2-instance"

#   ami                    = "ami-0ceecbb0f30a902a6"
#   instance_type          = "t2.micro"
#   key_name               = "Showing-off-keys"#case-sensitive
#   monitoring             = false
#   vpc_security_group_ids = ["sg-0856b724d5162426a", "sg-06a28db6f1f79f4d1"] # you can place multiple security groups in your list

#   tags = {
#     Terraform   = "true"
#     Environment = "dev"
#   }
# }

resource "aws_db_instance" "planetarium-p2" {
  identifier             = "planetarium-p2"
  instance_class         = "db.t3.micro"
  allocated_storage      = 20
  engine                 = "postgres"
  engine_version         = "14.1"
  username               = var.db_username
  password               = var.db_password
  db_name                = "planetarium"
  vpc_security_group_ids = ["sg-01892939cdb146fae", "sg-0856b724d5162426a"]
  multi_az               = false
  publicly_accessible    = true
  allow_major_version_upgrade = false
  auto_minor_version_upgrade = false
  skip_final_snapshot = true
}

resource "aws_s3_bucket" "terraform_state" {
  # if you get a "wrong region" error there is a good chance it is because of your bucket name
  bucket = "revature-p2-state-bucket" # this bucket name has already been taken, leaving it for now to show error message
  lifecycle {
    prevent_destroy = true # this makes sure terraform does not destroy the bucket
  }
  versioning {
    enabled = true # this makes the bucket keep track of the various objects placed inside
  }
  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }
}