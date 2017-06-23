VERSION=$(shell cat VERSION)
WAR=wrap-text\#$(VERSION).war
TGZ=wrap-text-$(VERSION).tgz

include ../master.mk

docker:
	cd src/main/docker && make
	
