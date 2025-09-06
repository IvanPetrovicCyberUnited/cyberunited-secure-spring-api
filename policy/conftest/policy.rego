package main

deny[msg] {
  input.Command == "FROM"
  endswith(input.Value, ":latest")
  msg = "Do not use latest tag"
}

deny[msg] {
  input.Command == "FROM"
  not contains(input.Value, "@sha256:")
  msg = "Image must be pinned with digest"
}
