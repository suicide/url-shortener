# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"
  config.vm.network "forwarded_port", guest: 3306, host: 3306
  config.vm.provision "shell", inline: <<-SHELL
    debconf-set-selections <<< 'mysql-server mysql-server/root_password password root'
    debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password root'

    apt-get update
    apt-get install -y mysql-server
    sed -i '/bind-address/d' /etc/mysql/mysql.conf.d/mysqld.cnf
    service mysql restart
    mysql -u "root" "-proot" < /vagrant/create.sql

  SHELL
end
