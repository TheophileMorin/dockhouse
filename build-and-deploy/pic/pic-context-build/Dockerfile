FROM      ubuntu:14.04
MAINTAINER Benoit Wilcox <wilcox.benoit@gmail.com>

RUN apt-get -y update

# INSTALLATION NEEDED FOR THE JENKINS SLAVE
##############################################
RUN apt-get install -y openssh-server
RUN useradd -m -s /bin/bash jenkins
RUN echo jenkins:jenkins | chpasswd
RUN mkdir -p /var/run/sshd

# LIBRAIRIES AND FRAMEWORKS NEEDED FOR THE APPLICATION
########################################################

# install python-software-properties
#----------------------------------------
RUN apt-get install -y -q python-software-properties software-properties-common

# install oracle java from PPA
#----------------------------------------
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get -y install oracle-java8-installer && apt-get clean

# Set oracle java as the default java
#----------------------------------------
RUN update-java-alternatives -s java-8-oracle
RUN echo "export JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> ~/.bashrc

# install utilities
#----------------------------------------
RUN apt-get install -y vim git sudo zip bzip2 fontconfig curl libc6-dev libc6 build-essential make

# install maven
#----------------------------------------
RUN apt-get -y install maven

# install node.js from PPA
#----------------------------------------
RUN add-apt-repository ppa:chris-lea/node.js
RUN apt-get update
RUN apt-get -y install nodejs

# install yeoman
#----------------------------------------
RUN npm install -g yo

# install bower
#----------------------------------------
RUN npm install -g bower

# install grunt CLI
#----------------------------------------
RUN npm install -g grunt-cli

# install Ruby, Compass and sass
#----------------------------------------
RUN apt-get install -y ruby-full
RUN gem install sass
RUN gem install compass

# Check version scripts
#----------------------------------------
ADD check-versions check-versions
RUN chmod +x ./check-versions

EXPOSE 22

CMD /usr/sbin/sshd -D
