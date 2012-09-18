#!/usr/bin/groovy


//Extract mediawiki page contents:
//go through each page
//write the first revision text into a file named .mediawiki

def file=args[0]

def mw = new groovy.xml.Namespace('http://www.mediawiki.org/xml/export-0.4/')
def x = new XmlParser().parse(file)

x.page.each{p->
    def dir=new File(".")
    def title= p[mw.title].text()
    def pfile=new File(dir,title+".mediawiki")
    pfile = new File(pfile.absolutePath)
    if(!pfile.exists()){
        pfile.parentFile.mkdirs();
        pfile.withWriter{w->
            w<<p[mw.revision][0][mw.text].text()
        }
        println "Wrote file: "+pfile
    }
    //println p[mw.revision][0][mw.text].text()
}