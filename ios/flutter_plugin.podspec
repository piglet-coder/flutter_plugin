#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
#
Pod::Spec.new do |s|
  s.name             = 'flutter_plugin'
  s.version          = '0.0.1'
  s.summary          = 'A new Flutter plugin.'
  s.description      = <<-DESC
A Flutter plugin for android used.
                       DESC
  s.homepage         = 'https://github.com/piglet-coder/flutter_plugin'
  s.license          = { :type => 'BSD', :file => '../LICENSE' }
  s.author           = { 'piglet-coder' => 'zdl328465042@163.com' }
  s.source           = { :http => 'https://github.com/piglet-coder/flutter_plugin' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.platform = :ios, '8.0'
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'VALID_ARCHS[sdk=iphonesimulator*]' => 'x86_64' }
end
