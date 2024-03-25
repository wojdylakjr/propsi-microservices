terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "5.21.0"
    }
  }
}
provider "google" {
  project = "propsi"
  region  = "europe-central2"
  zone    = "europe-central2-a"
}