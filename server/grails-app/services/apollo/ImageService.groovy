package apollo

class ImageService {
    boolean download(String urlSource, String pathDestination) {
        new File('grails-app/views/' + pathDestination).withOutputStream { out ->
            new URL(urlSource).withInputStream { from ->  out << from }
        }

        return true;
    }
}
