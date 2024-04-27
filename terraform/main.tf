resource "google_container_cluster" "propsi-cluster" {
  name                     = "propsi-cluster"
  location                 = "europe-central2-a"
  deletion_protection      = false
  remove_default_node_pool = true
  initial_node_count       = 1
  logging_service          = "none"
  monitoring_service       = "monitoring.googleapis.com/kubernetes"

}
resource "google_container_node_pool" "primary_preemptible_nodes" {
  name       = "my-node-pool"
  location   = "europe-central2-a"
  cluster    = google_container_cluster.propsi-cluster.name
  node_count = 1

  management {
    auto_repair  = true
    auto_upgrade = true
  }

  node_config {
    preemptible  = true
    machine_type = "e2-standard-8"
    disk_size_gb = 50
  }
}