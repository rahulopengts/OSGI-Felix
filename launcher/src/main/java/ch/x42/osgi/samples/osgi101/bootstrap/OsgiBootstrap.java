package ch.x42.osgi.samples.osgi101.bootstrap;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Bootstrap the OSGi framework, based on Neil Bartlett's 
 *  http://njbartlett.name/2011/03/07/embedding-osgi.html
 *  tutorial.
 *  Installs and starts all bundles found in a folder specified by 
 *  the "additional.bundles.path" system property.
 */
class OsgiBootstrap {
    private static final Logger log = LoggerFactory.getLogger(OsgiBootstrap.class);
    private final Framework framework;
    
    private static final String ADD_BUNDLES_FOLDER = System.getProperty("additional.bundles.path","target/bundles");
    
    OsgiBootstrap() throws BundleException {
        FrameworkFactory frameworkFactory = java.util.ServiceLoader.load(FrameworkFactory.class).iterator().next();
        final Map<String, String> config = new HashMap<String, String>();
        framework = frameworkFactory.newFramework(config);
        framework.start();
        log.info("OSGi framework started");
    }
    
    void installBundles(File fromFolder) throws BundleException {
        final String[] files = fromFolder.list();
        if(files == null) {
            log.warn("No bundles found in {}", fromFolder.getAbsolutePath());
            return;
        }
        
        log.info("Installing bundles from {}", fromFolder.getAbsolutePath());
        final List<Bundle> installed = new LinkedList<Bundle>();
        final BundleContext ctx = framework.getBundleContext();
        for(String filename : files) {
            if(filename.endsWith(".jar")) {
                final File f = new File(fromFolder, filename);
                final String ref = "file:" + f.getAbsolutePath();
                log.info("Installing bundle {}", ref);
                installed.add(ctx.installBundle(ref));
            }
        }
        
        for (Bundle bundle : installed) {
            log.info("Starting bundle {}", bundle.getSymbolicName());
            bundle.start();
        }
        
        log.info("{} bundles installed from {}", installed.size(), fromFolder.getAbsolutePath());
    }
    
    void waitForFrameworkAndQuit() throws Exception {
        try {
            framework.waitForStop(0);
        } finally {
            log.info("OSGi framework stopped, exiting");
            System.exit(0);
        }        
    }
    
    Framework getFramework() {
        return framework;
    }
    
    public static void main(String [] args) throws Exception {
        final OsgiBootstrap osgi = new OsgiBootstrap();
        final Framework framework = osgi.getFramework();
        
        log.info("Framework bundle: {} ({})", framework.getSymbolicName(), framework.getState());
        log.info("Looking for additional bundles under {}", ADD_BUNDLES_FOLDER);
        osgi.installBundles(new File(ADD_BUNDLES_FOLDER));
        for(Bundle b : framework.getBundleContext().getBundles()) {
            log.info("Installed bundle: {} ({})", b.getSymbolicName(), b.getState());
        }
        
        osgi.waitForFrameworkAndQuit();
    }
}