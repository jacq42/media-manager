package de.jkrech.mediamanager.quality

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.core.importer.Location
import com.tngtech.archunit.thirdparty.com.google.common.collect.ImmutableSet

import java.util.regex.Pattern

class ExcludeTestClasses implements ImportOption {

  private static final Pattern MAVEN_PATTERN = Pattern.compile(".*/target/test-classes/.*");
  private static final Pattern GRADLE_PATTERN = Pattern.compile(".*/build/classes/([^/]+/)?test/.*");
  private static final Pattern INTELLIJ_PATTERN = Pattern.compile(".*/out/test/classes/.*");
  private static final Pattern ECLIPSE_PATTERN = Pattern.compile(".*/bin/test/.*");

  private static final Set<Pattern> EXCLUDED_PATTERN = ImmutableSet.of(MAVEN_PATTERN, GRADLE_PATTERN, INTELLIJ_PATTERN, ECLIPSE_PATTERN);

  @Override
  public boolean includes(Location location) {
    for (Pattern pattern : EXCLUDED_PATTERN) {
      if (location.matches(pattern)) {
        return false;
      }
    }
    return true;
  }
}
