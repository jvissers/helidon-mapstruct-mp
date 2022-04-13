package io.helidon.mapstruct.mp;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

//@Decorator
abstract class SourceTargetMapperDecorator implements SourceTargetMapper {

    @Inject
    @Delegate
    private SourceTargetMapper delegate;

    @Override
    public Target toTarget(Source source) {
        System.err.println("___Decorating___ no enclosing type");

        Target t = delegate.toTarget(source);
        t.setName("I got decorated");

        return t;
    }
}