#!/bin/sh
ansible-playbook -i inventory/qa site.yml
# ansible-playbook -i hosts -l application site.yml
