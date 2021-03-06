package com.rackspace.papi.commons.util.plugin.archive;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;

import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import static org.mockito.Mockito.*;

/**
 * @author fran
 */
@RunWith(Enclosed.class)
public class ManifestProcessorTest {
    public static class WhenProcessingManifest {
        /*
        @Test
        public void shouldInstantiate () {
            ManifestProcessor processor = new ManifestProcessor();

            assertNotNull(processor);
        }
         * 
         */

        @Test
        public void shouldDoNothingForNullManifest () {
            ArchiveEntryDescriptor mockedArchiveEntryDescriptor = mock(ArchiveEntryDescriptor.class);
            JarInputStream mockedInputStream = mock(JarInputStream.class);
            ArchiveEntryListener mockedListener = mock(ArchiveEntryListener.class);

            when(mockedInputStream.getManifest()).thenReturn(null);

            ManifestProcessor.processManifest(mockedArchiveEntryDescriptor, mockedInputStream, mockedListener);
        }

        @Test
        public void shouldCallListenerToProcessManifest() {
            ArchiveEntryDescriptor mockedArchiveEntryDescriptor = mock(ArchiveEntryDescriptor.class);
            JarInputStream mockedInputStream = mock(JarInputStream.class);
            ArchiveEntryListener mockedListener = mock(ArchiveEntryListener.class);
            Manifest mockedManifest = mock(Manifest.class);

            when(mockedInputStream.getManifest()).thenReturn(mockedManifest);
            doNothing().when(mockedListener).newJarManifest(any(ArchiveEntryDescriptor.class), any(Manifest.class));

            ManifestProcessor.processManifest(mockedArchiveEntryDescriptor, mockedInputStream, mockedListener);
        }
    }
}
