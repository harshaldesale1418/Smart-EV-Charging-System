provider "aws" {
  region = "eu-west-1"
  profile = "ec2_instance_user"
}

# fetch default vpc
data "aws_vpc" "default" {
  default = true
}

#fetch subnets in vpc
data "aws_subnet_ids" "default" {
  vpc_id = data.aws_vpc.default.id
}
#fetch specific subnet
data "aws_subnet" "default" {
  id = data.aws_subnet_ids.default.ids[0]
}


# Create security group
resource "aws_security_group" "instance_sg" {
  name        = "instance_sg"
  description = "Allow SSH and HTTP traffic"

  vpc_id = aws_vpc.evchagingstation.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

data "aws_ami" "ubuntu" {
  most_recent = true

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  owners = ["099720109477"]
}


resource "aws_instance" "evstation_backend" {
  ami           = data.aws_ami.ubuntu.id
  instance_type = "t2.micro"
  subnet_id     = aws_subnet.public_subnet.id
  key_name      = "backend"   # Change to your key p


  vpc_security_group_ids = [aws_security_group.instance_sg.id]

  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update && sudo apt-get install -y openjdk-21-jdk
              EOF
}


output "public_ip" {
  value = aws_instance.evstation_backend.public_ip
}
