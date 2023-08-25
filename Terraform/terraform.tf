terraform {
  required_providers {
    # aws is the name we are giving our required provider: its configuration details is handled by the "source"
    # in this case, hashicorp/aws
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }

  backend "s3" {
    bucket = "revature-p2-state-bucket"
    profile = "terraform-user"
    region = "us-west-2"
    key = "state/terraform.tfstate"
    encrypt = true
  }
}