package io.helidon.mapstruct.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Service {

    @Inject
    private SourceTargetMapper sourceTargetMapper;

    public Target mapIt(Source source) {
        return sourceTargetMapper.toTarget(source);
    }
}
