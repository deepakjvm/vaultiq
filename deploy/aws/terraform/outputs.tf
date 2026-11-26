output "cloudfront_distribution_id" {
  description = "CloudFront Distribution ID for cache invalidation"
  value       = aws_cloudfront_distribution.frontend.id
}

output "cloudfront_domain_name" {
  description = "CloudFront Distribution domain name"
  value       = aws_cloudfront_distribution.frontend.domain_name
}

output "s3_bucket_name" {
  description = "S3 bucket name hosting the frontend assets"
  value       = aws_s3_bucket.frontend.bucket
}

output "backend_public_ip" {
  description = "Public Elastic IP address of the EC2 backend instance"
  value       = aws_eip.backend.public_ip
}
