import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './guarantee-production.reducer';
import { IGuaranteeProduction } from 'app/shared/model/guarantee-production.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGuaranteeProductionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GuaranteeProductionDetail = (props: IGuaranteeProductionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { guaranteeProductionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="tinhocdanangApp.guaranteeProduction.detail.title">GuaranteeProduction</Translate> [
          <b>{guaranteeProductionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <Translate contentKey="tinhocdanangApp.guaranteeProduction.production">Production</Translate>
          </dt>
          <dd>{guaranteeProductionEntity.production ? guaranteeProductionEntity.production.id : ''}</dd>
          <dt>
            <Translate contentKey="tinhocdanangApp.guaranteeProduction.guarantee">Guarantee</Translate>
          </dt>
          <dd>{guaranteeProductionEntity.guarantee ? guaranteeProductionEntity.guarantee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/guarantee-production" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/guarantee-production/${guaranteeProductionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ guaranteeProduction }: IRootState) => ({
  guaranteeProductionEntity: guaranteeProduction.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GuaranteeProductionDetail);
