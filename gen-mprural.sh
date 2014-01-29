#!/usr/bin/expect -f
set timeout 100
spawn maven andromdapp:generate

expect "Please enter your first and last name (i.e. Rodrigo Salvador):" 
send "Filipe Braida\r"

expect "Please enter the name of your J2EE project (i.e. Sistema Academico):"
send "MP Rural\r"

expect "Please enter the id for your J2EE project (i.e. sistemaacademico):"
send "mprural\r"

expect "Please enter a version for your project (i.e. 1.0):"
send "1.0\r"

expect "Please enter the base package name for your J2EE project (i.e. br.mdarte.exemplo.academico):"
send "br.ufrrj.maratona\r"

expect "Would you like to enable security? (enter 'yes' or 'no')?"
send "yes\r"

expect "Would you like to use oAuth (enter 'yes' or 'no') ?"
send "no\r"

expect "Would you like to use MDArte's default Controle Acesso (enter 'yes' or 'no') ?"
send "yes\r"

expect "Would you like to use modules (enter 'yes' or 'no')?"
send "yes\r"

expect "Please enter the EJB version number (enter '2' or '3'):"
send "3\r"

expect "Please enter the Struts version number (enter '1' or '2'):"
send "2\r"

expect "Would you like to enable the JUnit support for general testing? (enter 'yes' or 'no')?"
send "no\r"

expect "Please enter the database backend for the persistence layer: (enter 'hypersonic' or 'mysql' or 'oracle' or 'postgres')"
send "postgres\r"

interact