package main

denied[msg] {
  input[i].Cmd == "from"
  endswith(lower(input[i].Value[0]), ":latest")
  msg := "base image uses latest tag"
}

denied[msg] {
  input[i].Cmd == "from"
  not contains(input[i].Value[0], "@sha256")
  msg := "base image not pinned by digest"
}

denied[msg] {
  not some i
  input[i].Cmd == "user"
  msg := "no USER specified"
}

denied[msg] {
  input[i].Cmd == "user"
  input[i].Value[0] == "root"
  msg := "container runs as root"
}
