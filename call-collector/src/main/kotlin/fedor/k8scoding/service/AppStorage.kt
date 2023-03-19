package fedor.k8scoding.service

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AppStorage(val resps: MutableSet<String> = mutableSetOf())