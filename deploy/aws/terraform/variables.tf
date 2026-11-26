variable "aws_region" {
  type        = string
  default     = "ap-southeast-1"
  description = "AWS deployment region"
}

variable "environment" {
  type        = string
  default     = "production"
  description = "Target deployment environment"
}

variable "project_name" {
  type        = string
  default     = "vaultiq"
  description = "Project name prefix"
}

variable "instance_type" {
  type        = string
  default     = "t4g.small"
  description = "EC2 instance type (AWS Graviton2 ARM64)"
}

variable "ssh_key_name" {
  type        = string
  default     = "vaultiq-deploy-key"
  description = "Name of the AWS EC2 Key Pair for SSH access"
}
