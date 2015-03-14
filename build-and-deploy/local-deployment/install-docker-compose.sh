#!/bin/sh
curl -L https://github.com/docker/compose/releases/download/1.1.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

# Completion installation
curl -L https://raw.githubusercontent.com/docker/compose/1.1.0/contrib/completion/bash/docker-compose > /etc/bash_completion.d/docker-compose
